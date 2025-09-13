import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { fetchCompletedQuizQuestion, fetchHistoryQuizOfUser } from "@/services/quiz_attempt/quizAttempt";
import { QuizAttempt } from "@/types/quizAttempt";



export function useCompletedQuizQuestion() {
  const queryClient = useQueryClient();

  return useMutation<QuizAttempt, Error, QuizAttempt>({
    mutationFn: (data) => fetchCompletedQuizQuestion(data).then(res => res.data),

    onSuccess: (lessonProgress, variables) => {
      queryClient.invalidateQueries({ queryKey: ['quiz_attempt', variables.quizId] });
    },

    onError: (error, variables, context) => {
      console.error('Error updating user:', error);
    },

  });
}

export function useGetHistoryQuiz(idQuiz : number) {
    return useQuery({
        queryKey : ['quiz_attempt', idQuiz],
        queryFn : () => fetchHistoryQuizOfUser(idQuiz).then(res => res.data)
    })
}
