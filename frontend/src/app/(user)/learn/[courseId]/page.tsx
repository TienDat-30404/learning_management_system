'use client'

import React, { useState, useRef, useEffect, useCallback } from 'react';
import {
  Play,
  BookOpen,
  CheckCircle,
  Settings,
  Menu,
  X,
} from 'lucide-react';
import { useGetAllLessons } from '@/hooks/useLessons';
import { useParams, useSearchParams } from 'next/navigation';
import { Lesson } from '@/types/lesson';
import { useCompletedLesson, useGetTotalLessonCompleted } from '@/hooks/useLessonProgress';
import { useCheckExistLessonQuiz } from '@/hooks/useQuizs';
import { useRouter } from 'next/navigation';

const CourseLearningInterface = () => {
  const router = useRouter()
  const { courseId } = useParams()
  const { data: lessons, isLoading, error } = useGetAllLessons({
    courseId: courseId
  })

  const [sidebarOpen, setSidebarOpen] = useState(true);
  const videoRef = useRef<HTMLVideoElement>(null);
  const [isPlaying, setIsPlaying] = useState(false);
  const [currentLesson, setCurrentLesson] = useState<Lesson>(lessons?.data?.content[0]);


  const { data: totalLessonCompeleted } = useGetTotalLessonCompleted({
    courseId: courseId
  })


  const selectLesson = (lesson: Lesson) => {
    setCurrentLesson(lesson);
    setIsPlaying(false);
  };

  const { data: hasQuiz, isError } = useCheckExistLessonQuiz(currentLesson?.id);
  const {mutate : markLessonCompleted} = useCompletedLesson();
  const handleCompleteLesson = () => {
    if(hasQuiz) {
      router.push(`/quiz/${currentLesson?.id}`)
    }
    else {
      markLessonCompleted({
        lessonId : currentLesson?.id,
        courseId : Number(courseId)
      })
    }
  }

  

  console.log("hasssQUixzzz", hasQuiz)
  return (
    <div className="min-h-screen bg-gray-900 text-white">

      {/* Header */}
      <div className="bg-gray-800 border-b border-gray-700">
        <div className="px-4 sm:px-6 lg:px-8">
          <div className="flex items-center justify-between h-16">
            <div className="flex items-center space-x-4">
              <button
                onClick={() => setSidebarOpen(!sidebarOpen)}
                className="p-2 text-gray-400 hover:text-white transition-colors lg:hidden"
              >
                {sidebarOpen ? <X className="w-5 h-5" /> : <Menu className="w-5 h-5" />}
              </button>
              <BookOpen className="w-6 h-6 text-blue-400" />
              <div>
                <h1 className="text-lg font-semibold text-white truncate max-w-md">
                  {lessons?.data?.course?.title}
                </h1>
                <p className="text-sm text-gray-400">by Nguyễn Văn A</p>
              </div>
            </div>
            <div className="flex items-center space-x-4">
              <div className="hidden sm:flex items-center space-x-2">
                <span className="text-sm text-gray-400">Tiến độ:</span>
                <div className="w-32 bg-gray-700 rounded-full h-2">
                  <div
                    className="bg-blue-500 h-2 rounded-full transition-all duration-300"
                  // style={{ width: `${courseData.progress}%` }}
                  ></div>
                </div>
                {/* <span className="text-sm text-gray-400">{courseData.progress}%</span> */}
              </div>
              <button className="p-2 text-gray-400 hover:text-white transition-colors">
                <Settings className="w-5 h-5" />
              </button>
            </div>
          </div>
        </div>
      </div>

      <div className="flex ">
        {/* Sidebar */}
        <div className={`${sidebarOpen ? 'w-80' : 'w-0'} transition-all duration-300 overflow-hidden bg-gray-800 border-r border-gray-700`}>
          <div className="h-full overflow-y-auto">
            <div className="p-4 border-b border-gray-700">
              <h2 className="text-lg font-semibold text-white mb-2">Nội dung khóa học</h2>
              <div className="text-sm text-gray-400">
                {totalLessonCompeleted} / {lessons?.data?.content?.length} bài học đã hoàn thành
              </div>
            </div>

            <div className="p-4 space-y-2">
              {lessons?.data?.content?.map((lesson: Lesson) => (
                <div key={lesson.id} className={`${lesson?.id === currentLesson?.id ? 'bg-gray-700' : ''} flex items-center border px-2 border-gray-700 rounded-lg overflow-hidden`}>
                  <button
                    onClick={() => selectLesson(lesson)}
                    className="w-full p-3 bg-gray-750 hover:bg-gray-700 transition-colors flex items-center justify-between"
                  >
                    <span className="font-medium text-white text-left">{lesson.title}</span>
                  </button>
                  <CheckCircle className='text-green-300' />
                </div>
              ))}
            </div>
          </div>
        </div>

        {/* Main Content */}
        <div className="flex-1 flex flex-col overflow-hidden ">
          {/* Video Player */}


          <div className="relative bg-black aspect-video">
            {isPlaying ? (

              <video
                controls
                ref={videoRef}
                src={currentLesson?.videoUrl}
                className="w-full h-full object-contain bg-black"
                preload="auto"
              />
            ) : (

              <div className="absolute inset-0 flex items-center justify-center">
                <div className="w-full h-full bg-gradient-to-br from-gray-800 to-gray-900 flex items-center justify-center">
                  <div className="text-center">
                    <div className="w-24 h-24 bg-blue-600 rounded-full flex items-center justify-center mx-auto mb-4">
                      <Play
                        onClick={() => setIsPlaying(true)}
                        className="w-12 h-12 text-white ml-1" />
                    </div>
                    <h3 className="text-xl font-semibold text-white mb-2">
                      {currentLesson?.title}
                    </h3>
                  </div>
                </div>
              </div>
            )}

          </div>

          {/* Content Area */}
          <div className="flex-1 overflow-y-auto bg-gray-900">
            <div className="p-6">
              {/* Lesson Header */}
              <div className="mb-6">
                <h2 className="text-2xl font-bold text-white mb-2">{currentLesson?.title}</h2>
                <p className="text-gray-400 mb-4">{currentLesson?.content}</p>

                <div className="flex items-center space-x-4">
                  <button
                    onClick={() => handleCompleteLesson()}
                    className={`px-4 py-2 rounded-lg font-medium transition-colors cursor-pointer
                      ${currentLesson?.isCompleted
                        // completedLessons.has(currentLesson)
                        ? 'bg-green-600 text-white cursor-not-allowed'
                        : 'bg-blue-600 hover:bg-blue-700 text-white'
                      }`}
                  >
                    {currentLesson?.isCompleted
                      ? (
                        <>
                          <CheckCircle className="w-4 h-4 mr-2 inline" />
                          Đã hoàn thành
                        </>
                      ) : (
                        hasQuiz ? 'Hoàn thành bài học với quiz' : 'Đánh dấu hoàn thành bài học'
                      )}
                  </button>
                </div>
              </div>

            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CourseLearningInterface;