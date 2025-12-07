'use client'

import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { RootState } from '@/store';
import { HydrationBoundary, DehydratedState } from '@tanstack/react-query';


import IconChat from '@/components/ui/chat/IconChat';
import HeroSection from '@/features/home/components/HeroSection';
import StatsSection from '@/features/home/components/StatsSection';
import CategoryHomePageComponent from '@/features/home/components/CategoryHomePageComponent';
import ContinueLearning from '@/features/home/components/ContinueLearning';
import Banner from '@/features/home/components/Banner';
import GetStartedSection from '@/features/home/components/GetStartedSection';
import WelcomeSection from '@/features/home/components/WelcomeSection';
import FeaturedCourses from '@/features/course-listing/components/FeaturedCourses';


interface HomeClientProps {
    dehydratedState: DehydratedState;
}

const HomeClient: React.FC<HomeClientProps> = ({ dehydratedState }) => {
    const { isAuthenticated, userInfo } = useSelector((state: RootState) => state.user);


    return (

        <div className="min-h-screen bg-gray-50">
            <HydrationBoundary state={dehydratedState}>

                {isAuthenticated && userInfo?.role === 'User' ? (
                    <>
                        <HeroSection />
                        <StatsSection />
                        <IconChat />
                        <CategoryHomePageComponent />
                        <ContinueLearning />
                    </>
                ) : (
                    <>
                        <Banner />
                        <WelcomeSection />
                        <GetStartedSection />
                        <CategoryHomePageComponent />
                    </>

                )}
                <FeaturedCourses
                    dataFilter='continued-learning'
                    title="Khóa Học Của Bạn"
                    subtitle="Tiếp tục học tập và khám phá các khóa học bạn đã đăng ký"
                />
            </HydrationBoundary>
        </div>
    );
};

export default HomeClient;