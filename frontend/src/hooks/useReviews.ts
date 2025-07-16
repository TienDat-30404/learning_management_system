import { getAllCourse } from "@/services/courses/courses";
import { getAllReview } from "@/services/reviews/reviews";
import { QueryParams } from "@/types/common";
import { useQuery } from "@tanstack/react-query";

export function useGetAllReview(params : QueryParams) {
    return useQuery({
        queryKey : ['reviews', params],
        queryFn : () => getAllReview(params).then(res => res.data)
    })
}


