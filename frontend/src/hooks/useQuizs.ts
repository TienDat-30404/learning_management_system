import { checkExistLessonQuiz, getAllQuestionOfQuiz } from "@/services/quizs/quizs";
import { QueryParams } from "@/types/common";
import { useQuery } from "@tanstack/react-query";

export function useGetAllQuestionOfQuiz(params : QueryParams) {
    return useQuery({
        queryKey : ['quizs', params],
        queryFn : () => getAllQuestionOfQuiz(params).then(res => res.data)
    })
}

export function useCheckExistLessonQuiz(lessonId: number) {
  return useQuery<boolean, Error>({ 
    queryKey: ['quizs', lessonId], 
    queryFn: async () => {
      const response = await checkExistLessonQuiz(lessonId);
      return response.data as boolean;
    },
  });
}