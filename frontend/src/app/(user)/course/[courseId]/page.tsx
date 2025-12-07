
import { CourseDetailsClient } from "@/features/course-detail/components/DetailCourse";
import { getDetailCourse } from "@/services/courses/courses";
import { dehydrate, QueryClient, HydrationBoundary } from '@tanstack/react-query';

export default async function CourseDetailPage({ params }: { params: { courseId: string } }) {
  const courseId = Number(params.courseId);

  const queryClient = new QueryClient();

  await queryClient.prefetchQuery({
    queryKey: ['courses', courseId],
    queryFn: () => getDetailCourse(courseId).then(res => res.data),
  });

  const dehydratedState = dehydrate(queryClient);

  return (
    <HydrationBoundary state={dehydratedState}>
      <div className="max-w-7xl mx-auto px-4 py-8">

          <CourseDetailsClient
            courseId={courseId}
          />

      </div>
    </HydrationBoundary>
  );
}