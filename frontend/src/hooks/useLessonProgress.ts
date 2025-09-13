import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { QueryParams } from "@/types/common";
import { fetchCompletedLesson, getTotalLessonCompleted } from "@/services/lesson_progress/lessonProgress";
import { LessonProgress } from "@/types/lessonProgress";

export function useGetTotalLessonCompleted(params: QueryParams) {
  return useQuery({
    queryKey: ["lesson_progress", params],
    queryFn: () => getTotalLessonCompleted(params).then(res => res.data)
  });
}


export function useCompletedLesson() {
  const queryClient = useQueryClient();

  return useMutation<LessonProgress, Error, LessonProgress>({
    mutationFn: (data) => fetchCompletedLesson(data).then(res => res.data),

    onSuccess: (lessonProgress, variables) => {
      queryClient.invalidateQueries({ queryKey: ['lesson_progress', variables.lessonId] });
      queryClient.invalidateQueries({ queryKey: ['quizs', variables.lessonId] });
      queryClient.setQueryData(['lesson_progress', variables.lessonId], lessonProgress);
      queryClient.invalidateQueries({ queryKey: ['lessons', variables.courseId] });
    },

    onError: (error, variables, context) => {
      console.error('Error updating user:', error);
    },

  });
}
