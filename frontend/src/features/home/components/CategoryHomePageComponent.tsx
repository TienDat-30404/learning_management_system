'use client'
import React, { useEffect, useState } from 'react';
import Link from 'next/link';
import { useGetAllCategories } from '@/hooks/useCategories';
import { Category } from '@/types/category';
import { ChevronLeft, ChevronRight } from 'lucide-react';

const CategoryHomePageComponent: React.FC = () => {
    

    const [page, setPage] = useState<number>(0)
    const { data: categories, isLoading, error } = useGetAllCategories({page: Number(page), size : 6});

    const handleSwitchPage = (page : number) => {
        setPage(page)
    }


   


    return (
        <section className="py-16 bg-gray-50">
            <div className="container mx-auto px-4">
                
                {/* Danh mục phổ biến */}
                <div className="mb-16">
                    <h3 className="text-2xl font-bold text-gray-900 text-center mb-8">
                        Danh Mục Phổ Biến
                    </h3>
                    <div className="flex items-center gap-4">
                        <button onClick={() => handleSwitchPage(page - 1)}>
                            <ChevronLeft className="text-gray-500 w-6 h-6 cursor-pointer" />
                        </button>

                        <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-6 gap-4 flex-1">
                            {categories?.data?.content.map((category: Category) => (
                                <Link
                                    key={category?.id}
                                    href={`/courses?category=${category.name.toLowerCase()}`}
                                    className="block p-4 bg-white rounded-lg shadow-md hover:shadow-lg transition-shadow duration-200"
                                >
                                    <div className="inline-block px-2 py-1 rounded-full text-xs font-medium mb-2 bg-blue-100 text-blue-800">
                                        10000 khóa học
                                    </div>
                                    <div className="font-semibold text-gray-900">{category.name}</div>
                                </Link>
                            ))}
                        </div>

                        <button onClick={() => handleSwitchPage(page + 1)}>
                            <ChevronRight className="text-gray-500 w-6 h-6 cursor-pointer" />
                        </button>
                    </div>

                </div>

            </div>
        </section>
    );
};

export default CategoryHomePageComponent;