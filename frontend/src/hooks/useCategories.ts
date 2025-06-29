import { useQuery } from "@tanstack/react-query";
import { getAllCategory } from "@/services/categories/categories";
import { QueryParams } from "@/types/common";

export function useGetAllCategories(params: QueryParams) {
  return useQuery({
    queryKey: ["categories", params],
    queryFn: () => getAllCategory(params).then(res => res.data)
  });
}
