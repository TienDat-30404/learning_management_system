import { useQuery } from "@tanstack/react-query";
import { getAllCourse } from "@/services/courses/courses";
import { QueryParams } from "@/types/common";

export function useGetAllCourse(params : QueryParams) {
    return useQuery({
        queryKey : ['courses', params],
        queryFn : () => getAllCourse(params).then(res => res.data)
    })
}