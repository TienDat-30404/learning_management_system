  import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
  import { getAllCourse, getDetailCourse, updateCourse } from "@/services/courses/courses";
  import { QueryParams } from "@/types/common";
  import { Course, UpdateCourse } from "@/types/course";

  export function useGetAllCourse(params: QueryParams) {
    return useQuery({
      queryKey: ['courses', params],
      queryFn: () => getAllCourse(params).then(res => res.data),
      refetchOnMount: false,
    })
  }

  export function useGetDetailCouse(id: number) {
    return useQuery({
      queryKey: ['detailCourse', id],
      queryFn: () => getDetailCourse(id).then(res => res.data)
    })
  }

  export function useUpdateCourse(params: QueryParams) {
    const queryClient = useQueryClient();
    return useMutation<Course, Error, { id: number; data: FormData }>({
      mutationFn: ({ id, data }) => updateCourse(id, data).then(res => res.data),

      onSuccess: (updatedCourse, variables) => {
        queryClient.invalidateQueries({ queryKey: ['dashboard_teacher', params] });
        queryClient.invalidateQueries({ queryKey: ['courses', updatedCourse.id] });    },

      onError: (error, variables, context) => {
        console.error('Error updating course:', error);
      },

    });
  }

