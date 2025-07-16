import { useQuery } from "@tanstack/react-query";
import { QueryParams } from "@/types/common";
import { getTotalLessonCompleted } from "@/services/lesson_progress/lessonProgress";

export function useGetTotalLessonCompleted(params: QueryParams) {
  return useQuery({
    queryKey: ["lesson_progress", params],
    queryFn: () => getTotalLessonCompleted(params).then(res => res.data)
  });
}
