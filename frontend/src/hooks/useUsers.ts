
import { getDetailUser, updateUser } from "@/services/users/users";
import { User, UserUpdateData } from "@/types/user";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";

export function useGetDetailUser(idUser : number) {
    return useQuery({
        queryKey : ['reviews', idUser],
        queryFn : () => getDetailUser(idUser).then(res => res.data)
    })
}

export function useUpdateUser() {
  const queryClient = useQueryClient();

  return useMutation<User, Error, { id: number; data: UserUpdateData }>({
    mutationFn: ({ id, data }) => updateUser(id, data).then(res => res.data),

    onSuccess: (updatedUser, variables) => {
      queryClient.invalidateQueries({ queryKey: ['reviews', variables.id] }); 
      queryClient.invalidateQueries({ queryKey: ['users', variables.id] }); 

      queryClient.setQueryData(['reviews', variables.id], updatedUser);
      console.log('User updated successfully:', updatedUser);
    },

    onError: (error, variables, context) => {
      console.error('Error updating user:', error);
    },

  });
}