import { createFileRoute } from '@tanstack/react-router';
import ActivityFeed from '../../components/ActivityFeed';

export const Route = createFileRoute('/_authenticated/dashboard')({
  component: RouteComponent,
});

function RouteComponent() {
  return <ActivityFeed />;
}
