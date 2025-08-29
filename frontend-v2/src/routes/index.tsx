import { createFileRoute } from '@tanstack/react-router'
import LoginForm from '../components/Forms/Login';
import AuthLayout from '../components/AuthLayout';

export const Route = createFileRoute('/')({
  component: RouteComponent,
})

function RouteComponent() {
  return (
    <AuthLayout title="Sign In" form={<LoginForm />} />
  )
}
