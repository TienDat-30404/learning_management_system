import { fetchDataDashboardTeacher, fetchDataStudentsOfTeacher } from "@/services/teacher/teacher";
import { QueryParams } from "@/types/common";
import { useQuery } from "@tanstack/react-query";

export function useDashboardTeacher(params : QueryParams) {
    return useQuery({
        queryKey : ['dashboard_teacher', params],
        queryFn : () => fetchDataDashboardTeacher(params).then(res => res.data)
    })
}


export function useListStudentsOfTeacher(params : QueryParams) {
    return useQuery({
        queryKey : ['listStudentOfTeacher', params],
        queryFn : () => fetchDataStudentsOfTeacher(params).then(res => res.data)
    })
}