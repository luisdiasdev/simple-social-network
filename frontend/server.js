const express = require('express');
const { createProxyMiddleware } = require('http-proxy-middleware');
const path = require('path');
const app = express();
const PORT = process.env.PORT || 5000;
const API_APP_NAME = process.env.API_APP_NAME;

app.use(express.static(path.join(__dirname, 'build')));

const proxyMiddleware = createProxyMiddleware('/api', {
  target: `https://${API_APP_NAME}.herokuapp.com`,
  pathRewrite: {
    '^/api': '/'
  },
  changeOrigin: true,
});

app.use('/api', proxyMiddleware);

app.get('*', function (req, res) {
 res.sendFile(path.join(__dirname, 'build', 'index.html'));
});

app.listen(PORT);
