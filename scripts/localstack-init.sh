#!/bin/bash

echo "Initializing LocalStack S3..."

# Wait for LocalStack to be ready
echo "Waiting for LocalStack to start..."
until curl -s http://localhost:4566/_localstack/health > /dev/null 2>&1; do
  echo "LocalStack not ready yet, waiting..."
  sleep 2
done

# Wait specifically for S3 service to be available
echo "Waiting for S3 service to be available..."
until curl -s http://localhost:4566/_localstack/health | grep -q '"s3".*"available"'; do
  echo "S3 service not ready yet, waiting..."
  sleep 2
done

echo "LocalStack S3 is ready! Creating bucket..."

# Set AWS CLI to use LocalStack
export AWS_ACCESS_KEY_ID=test
export AWS_SECRET_ACCESS_KEY=test
export AWS_DEFAULT_REGION=us-east-1

# Create S3 bucket for the social network
echo "Creating S3 bucket..."
aws --endpoint-url=http://localhost:4566 s3 mb s3://social-network-bucket

# Verify bucket was created
if aws --endpoint-url=http://localhost:4566 s3 ls | grep -q "social-network-bucket"; then
  echo "✅ Bucket 'social-network-bucket' created successfully"
else
  echo "❌ Failed to create bucket"
  exit 1
fi

# Set bucket policy to allow public read access (for development only)
echo "Setting bucket policy..."
aws --endpoint-url=http://localhost:4566 s3api put-bucket-policy --bucket social-network-bucket --policy '{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "PublicReadGetObject",
      "Effect": "Allow",
      "Principal": "*",
      "Action": "s3:GetObject",
      "Resource": "arn:aws:s3:::social-network-bucket/*"
    }
  ]
}' || echo "⚠️  Warning: Could not set bucket policy (this is optional for development)"

# Enable CORS for the bucket
echo "Setting CORS configuration..."
aws --endpoint-url=http://localhost:4566 s3api put-bucket-cors --bucket social-network-bucket --cors-configuration '{
  "CORSRules": [
    {
      "AllowedHeaders": ["*"],
      "AllowedMethods": ["GET", "PUT", "POST", "DELETE"],
      "AllowedOrigins": ["*"],
      "ExposeHeaders": ["ETag"]
    }
  ]
}' || echo "⚠️  Warning: Could not set CORS configuration (this is optional for development)"

echo "✅ LocalStack S3 initialization completed!"
echo "📦 Bucket 'social-network-bucket' is ready for use"
echo "🔗 S3 endpoint: http://localhost:4566"
echo "🔑 Access Key: test"
echo "🔐 Secret Key: test"
echo "🌍 Region: us-east-1"