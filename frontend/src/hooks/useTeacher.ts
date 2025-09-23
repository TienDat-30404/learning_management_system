import { fetchDataDashboardTeacher } from "@/services/teacher/teacher";
import { QueryParams } from "@/types/common";
import { useQuery } from "@tanstack/react-query";

export function useDashboardTeacher(params : QueryParams) {
    return useQuery({
        queryKey : ['dashboard_teacher', params],
        queryFn : () => fetchDataDashboardTeacher(params).then(res => res.data)
    })
}