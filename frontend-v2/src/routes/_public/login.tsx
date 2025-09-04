import { createFileRoute } from '@tanstack/react-router';
import AuthLayout from '../../components/AuthLayout';
import LoginForm from '../../components/Forms/Login';

export const Route = createFileRoute('/_public/login')({
  component: RouteComponent,
});

function RouteComponent() {
  return <AuthLayout title="Sign In" form={<LoginForm />} />;
}
