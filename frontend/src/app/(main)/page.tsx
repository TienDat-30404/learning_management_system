'use client';

import React, { useState } from 'react';
// import Categories from '../components/home/Categories';
import Course from '../../components/home/Course';
import StatsSection from '../../components/home/StatsSection';
import ContinueLearning from '../../components/home/ContinueLearning';
import HeroSection from '../../components/home/HeroSection';

const Home: React.FC = () => {
    return (
        <div className="min-h-screen bg-gray-50">
            {/* HeroSection */}
            <HeroSection />
            
            {/* Stats Section */}
            <StatsSection />
            
            {/* Continue Learning */}
            <ContinueLearning />
            
            {/* Featured Courses */}
            <Course />
            
            {/* Categories */}
            {/* <Categories /> */}
        </div>
    );
};

export default Home;