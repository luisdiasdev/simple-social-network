import { createFileRoute } from '@tanstack/react-router'
import AuthLayout from '../components/AuthLayout';
import RegisterForm from '../components/Forms/Register';

export const Route = createFileRoute('/signup')({
  component: RouteComponent,
})

function RouteComponent() {
  return (
    <AuthLayout title="Sign Up" form={<RegisterForm />} />
  )
}
