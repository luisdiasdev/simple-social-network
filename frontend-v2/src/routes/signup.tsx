import { createFileRoute } from '@tanstack/react-router'
import { LoginPage } from '../pages/LoginPage'

export const Route = createFileRoute('/signup')({
  component: RouteComponent,
})

function RouteComponent() {
  return <LoginPage signup />
}
