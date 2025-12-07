

import { dehydrate, QueryClient } from '@tanstack/react-query';

import { QueryParams } from '@/types/common';
import HomeClient from './HomeClient'; // Component Client Wrapper
import { getAllCourse } from '@/services/courses/courses';
import { getAllCategory } from '@/services/categories/categories';


export default async function HomePageWrapper() {
    const queryClient = new QueryClient();
    
    const featuredParams: QueryParams = { page: 0, size: 4, filter: 'featured' };
    const categoryParams = { page: 0, size: 6 };

    await queryClient.prefetchQuery({
        queryKey: ['courses', featuredParams], 
        queryFn: () => getAllCourse(featuredParams),
        staleTime: 1000 * 60 * 1,
    });

    await queryClient.prefetchQuery({
        queryKey: ['categories', categoryParams], 
        queryFn: () => getAllCategory(categoryParams),
        staleTime: 1000 * 60 * 1,
    });


  

    const dehydratedState = dehydrate(queryClient);

    return (
        <HomeClient dehydratedState={dehydratedState} />
    );
}