import { useQuery } from "@tanstack/react-query";
import { QueryParams } from "@/types/common";
import { getCourseProgressOfUser } from "@/services/enrollments/enrollments";

export function useGetCourseProgressOfUser(params : QueryParams) {
    return useQuery({
        queryKey : ['enrollments', params],
        queryFn : () => getCourseProgressOfUser(params).then(res => res.data)
    })
}