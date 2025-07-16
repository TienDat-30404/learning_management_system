import { useQuery } from "@tanstack/react-query";
import { QueryParams } from "@/types/common";
import { getAllLesson } from "@/services/lessons/lessons";

export function useGetAllLessons(params : QueryParams) {
  return useQuery({
    queryKey: ["lessons", params],
    queryFn: () => getAllLesson(params).then(res => res.data)
  });
}
