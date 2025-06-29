'use client';

import React, { useEffect, useState } from 'react';
// import Categories from '../components/home/Categories';
import StatsSection from '../../components/home/StatsSection';
import ContinueLearning from '../../components/home/ContinueLearning';
import HeroSection from '../../components/home/HeroSection';
import Banner from '@/components/home/Banner';
import { Ban } from 'lucide-react';
import WelcomeSection from '@/components/home/WelcomeSection';
import GetStartedSection from '@/components/home/GetStartedSection';
import FeaturedCourses from '@/components/home/FeaturedCourses';
import { useSelector } from 'react-redux';
import { RootState } from '@/store';

const Home: React.FC = () => {
    const { isAuthenticated, userInfo } = useSelector((state: RootState) => state.user)
    return (
        <div className="min-h-screen bg-gray-50">

            {isAuthenticated ? (
                userInfo?.role === 'User' && (
                    <>
                        <HeroSection />

                        <StatsSection />

                        <ContinueLearning />
                        <FeaturedCourses />

                    </>

                )
            ) : (
                <>

                    <Banner />
                    < WelcomeSection />

                    <GetStartedSection />
                    <FeaturedCourses />

                </>
            )}

        </div>



    );
};

export default Home;