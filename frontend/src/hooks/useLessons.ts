import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { QueryParams } from "@/types/common";
import { createLesson, getAllLesson } from "@/services/lessons/lessons";
import { Lesson } from "@/types/lesson";

export function useGetAllLessons(params : QueryParams) {
  return useQuery({
    queryKey: ["lessons", params],
    queryFn: () => getAllLesson(params).then(res => res.data)
  });
}

export function useCreateLesson() {
  const queryClient = useQueryClient();
  return useMutation<Lesson, Error, {data: FormData }>({
    mutationFn: ({data}) => createLesson(data).then(res => res.data),

    onSuccess: (createdLesson, variables) => {
      // queryClient.invalidateQueries({ queryKey: ['dashboard_teacher', params] });
      queryClient.invalidateQueries({ queryKey: ['lessons', createdLesson.id] });    },

    onError: (error, variables, context) => {
      console.error('Error create lesson:', error);
    },

  });
}
