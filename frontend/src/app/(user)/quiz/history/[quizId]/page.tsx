'use client'
import React, { useState } from 'react';
import {
  Clock,
  Calendar,
  Trophy,
  Target,
  CheckCircle,
  XCircle,
  Eye,
  Filter,
  Search,
  TrendingUp,
  Award,
  BookOpen,
  ArrowRight
} from 'lucide-react';
import { useGetHistoryQuiz } from '@/hooks/useQuizAttempt';
import { useParams, useSearchParams } from 'next/navigation';
import { formatTime } from '@/utils/formatters';
import { QuizResult } from '@/types/quizAttempt';
import { useRouter } from 'next/navigation';

const QuizHistoryInterface: React.FC = () => {
  const router = useRouter()
  const [selectedFilter, setSelectedFilter] = useState<string>('all');
  const [searchTerm, setSearchTerm] = useState<string>('');

  const getScoreColor = (score: number) => {
    if (score >= 9) return 'text-green-600';
    if (score >= 8) return 'text-blue-600';
    if (score >= 7) return 'text-yellow-600';
    return 'text-red-600';
  };  

  // const filteredResults = quizResultss.filter(result => {
  //   const matchesFilter = selectedFilter === 'all' || result.category.toLowerCase() === selectedFilter;
  //   const matchesSearch = result.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
  //     result.category.toLowerCase().includes(searchTerm.toLowerCase());
  //   return matchesFilter && matchesSearch;
  // });

  const searchParams = useSearchParams();
  const lessonId = searchParams.get("lessonId")

  const { quizId } = useParams();
  const { data: quizResults, isLoading, isError } = useGetHistoryQuiz(Number(quizId));
  console.log("ggggggg", quizResults)
  const totalQuizResult = quizResults?.data?.length;

  const averageScore = quizResults?.data?.reduce((sum: number, result: QuizResult) => sum + result.score, 0) / totalQuizResult;
  const passedQuizzes = quizResults?.data?.filter((result: QuizResult) => result?.score > 7)?.length
  const totalTimeSpent = quizResults?.data?.reduce((sum: number, result: QuizResult) => sum + result.duration, 0);

  if (quizResults?.data?.length > 0) {

    return (
      <div className="min-h-screen bg-gradient-to-br from-blue-50 via-indigo-50 to-purple-50 p-6">
        <div className="max-w-7xl mx-auto">
          <div className="mb-8">
            <h1 className="text-4xl font-bold text-gray-900 mb-2 flex items-center gap-3">
              <BookOpen className="text-indigo-600" />
              Lịch sử làm bài Quiz
            </h1>
            <p className="text-gray-600 text-lg">Xem lại kết quả và theo dõi tiến độ học tập của bạn</p>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
            <div className="bg-white rounded-xl shadow-lg p-6 border border-gray-100 hover:shadow-xl transition-shadow duration-300">
              <div className="flex items-center justify-between">
                <div>
                  <p className="text-gray-600 text-sm font-medium">Tổng bài quiz</p>
                  <p className="text-3xl font-bold text-gray-900">{totalQuizResult}</p>
                </div>
                <div className="bg-blue-100 p-3 rounded-full">
                  <Target className="w-6 h-6 text-blue-600" />
                </div>
              </div>
            </div>

            <div className="bg-white rounded-xl shadow-lg p-6 border border-gray-100 hover:shadow-xl transition-shadow duration-300">
              <div className="flex items-center justify-between">
                <div>
                  <p className="text-gray-600 text-sm font-medium">Điểm trung bình</p>
                  <p className={`text-3xl font-bold ${getScoreColor(averageScore)}`}>{averageScore.toFixed(2)}</p>
                </div>
                <div className="bg-green-100 p-3 rounded-full">
                  <TrendingUp className="w-6 h-6 text-green-600" />
                </div>
              </div>
            </div>

            <div className="bg-white rounded-xl shadow-lg p-6 border border-gray-100 hover:shadow-xl transition-shadow duration-300">
              <div className="flex items-center justify-between">
                <div>
                  <p className="text-gray-600 text-sm font-medium">Đã hoàn thành</p>
                  <p className="text-3xl font-bold text-green-600">{passedQuizzes}</p>
                </div>
                <div className="bg-green-100 p-3 rounded-full">
                  <Trophy className="w-6 h-6 text-green-600" />
                </div>
              </div>
            </div>

            <div className="bg-white rounded-xl shadow-lg p-6 border border-gray-100 hover:shadow-xl transition-shadow duration-300">
              <div className="flex items-center justify-between">
                <div>
                  <p className="text-gray-600 text-sm font-medium">Thời gian học</p>
                  <p className="text-3xl font-bold text-purple-600">{formatTime(totalTimeSpent)}</p>
                </div>
                <div className="bg-purple-100 p-3 rounded-full">
                  <Clock className="w-6 h-6 text-purple-600" />
                </div>
              </div>
            </div>
          </div>

          <div className="bg-white rounded-xl shadow-lg p-6 mb-8 border border-gray-100">
            <div className="flex flex-col md:flex-row gap-4 items-center justify-between">
              <div className="flex items-center gap-4 flex-wrap">
                <div className="flex items-center gap-2">
                  <Filter className="w-5 h-5 text-gray-500" />
                  <span className="text-gray-700 font-medium">Lọc theo:</span>
                </div>
                {['all', 'programming', 'frontend', 'backend', 'design'].map((filter) => (
                  <button
                    key={filter}
                    onClick={() => setSelectedFilter(filter)}
                    className={`px-4 py-2 rounded-full text-sm font-medium transition-all duration-200 ${selectedFilter === filter
                      ? 'bg-indigo-600 text-white shadow-md'
                      : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
                      }`}
                  >
                    {filter === 'all' ? 'Tất cả' : filter.charAt(0).toUpperCase() + filter.slice(1)}
                  </button>
                ))}
              </div>

              <div className="relative">
                <Search className="w-5 h-5 text-gray-400 absolute left-3 top-1/2 transform -translate-y-1/2" />
                <input
                  type="text"
                  placeholder="Tìm kiếm bài quiz..."
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                  className="pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 outline-none transition-all duration-200"
                />
              </div>
            </div>
          </div>

          <div className="space-y-4">
            {quizResults?.data?.map((result: QuizResult) => (
              <div key={result.id} className="bg-white rounded-xl shadow-lg p-6 border border-gray-100 hover:shadow-xl transition-all duration-300 hover:border-indigo-200">
                <div className="flex flex-col lg:flex-row lg:items-center lg:justify-between gap-4">
                  <div className="flex-1">
                    <div className="flex items-center gap-3 mb-2">
                      <h3 className="text-xl font-bold text-gray-900">{result?.quiz?.title}</h3>
                      {/* <span className={`px-3 py-1 rounded-full text-xs font-medium ${getDifficultyColor(result.difficulty)}`}>
                        {result.difficulty.toUpperCase()}
                      </span> */}
                      {result.score > 7 ? (
                        <CheckCircle className="w-5 h-5 text-green-500" />
                      ) : (
                        <XCircle className="w-5 h-5 text-red-500" />
                      )}
                    </div>

                    <div className="flex items-center gap-6 text-sm text-gray-600 mb-3">
                      <div className="flex items-center gap-1">
                        <Calendar className="w-4 h-4" />
                        <span>{new Date(result?.createdAt)?.toLocaleDateString('vi-VN')}</span>
                      </div>
                      <div className="flex items-center gap-1">
                        <Clock className="w-4 h-4" />
                        <span>{formatTime(result?.duration)}</span>
                      </div>
                    </div>

                    <div className="flex items-center gap-4">
                      <div className="flex items-center gap-2">
                        <span className="text-gray-600">Điểm số:</span>
                        <span className={`text-2xl font-bold ${getScoreColor(result.score)}`}>
                          {result.score}
                        </span>
                      </div>
                      <div className="flex items-center gap-2">
                        <span className="text-gray-600">Đúng:</span>
                        <span className="font-semibold text-green-600">
                          {/* {result.correctAnswers}/{result.totalQuestions} */}
                          2/5
                        </span>
                      </div>
                    </div>
                  </div>

                  <div className="flex flex-col sm:flex-row gap-3">
                    <div className="bg-gray-50 rounded-lg p-4 text-center min-w-[100px]">
                      <div className={`text-2xl font-bold ${getScoreColor(result.score)} mb-1`}>
                        {result.score}
                      </div>
                      <div className="text-xs text-gray-600">Điểm số</div>
                    </div>

                    <button className="px-4 py-2 bg-indigo-600 text-white rounded-lg hover:bg-indigo-700 transition-colors duration-200 flex items-center gap-2 font-medium">
                      <Eye className="w-4 h-4" />
                      Xem chi tiết
                    </button>
                  </div>
                </div>

                <div className="mt-4 pt-4 border-t border-gray-100">
                  <div className="flex items-center justify-between text-sm text-gray-600 mb-2">
                    <span>Độ chính xác</span>
                    <span>{Math.round(result?.score * 10)}%</span>
                  </div>
                  <div className="w-full bg-gray-200 rounded-full h-2">
                    <div
                      className="bg-gradient-to-r from-indigo-500 to-purple-600 h-2 rounded-full transition-all duration-500"
                      style={{ width: `${(result?.score * 10)}%` }}
                    ></div>
                  </div>
                </div>
              </div>
            ))}
          </div>
          {/* 
          {filteredResults.length === 0 && (
            <div className="text-center py-12">
              <Award className="w-16 h-16 text-gray-300 mx-auto mb-4" />
              <h3 className="text-xl font-semibold text-gray-700 mb-2">Không tìm thấy kết quả</h3>
              <p className="text-gray-500">Thử thay đổi bộ lọc hoặc từ khóa tìm kiếm</p>
            </div>
          )} */}
        </div>
      </div>
    );
  }
  else {
    return (
      <div className={`flex flex-col items-center justify-center min-h-[400px] p-8 text-center`}>
        {/* Animated icon container */}
        <div className="relative mb-6 group">
          <div className="w-24 h-24 bg-gradient-to-br from-blue-100 to-indigo-100 rounded-full flex items-center justify-center mb-4 transition-transform duration-300 group-hover:scale-110">
            <BookOpen className="w-12 h-12 text-indigo-600" />
          </div>

          {/* Floating decorative icons */}
          <div className="absolute -top-2 -right-2 w-8 h-8 bg-yellow-100 rounded-full flex items-center justify-center animate-pulse">
            <Award className="w-4 h-4 text-yellow-600" />
          </div>
          <div className="absolute -bottom-1 -left-3 w-6 h-6 bg-green-100 rounded-full flex items-center justify-center animate-bounce">
            <TrendingUp className="w-3 h-3 text-green-600" />
          </div>
          <div className="absolute top-4 -left-4 w-5 h-5 bg-purple-100 rounded-full flex items-center justify-center animate-pulse delay-300">
            <Clock className="w-2.5 h-2.5 text-purple-600" />
          </div>
        </div>

        {/* Main heading */}
        <h3 className="text-3xl font-bold  mb-3 bg-gradient-to-r from-indigo-600 to-purple-600 bg-clip-text text-transparent">
          Chưa có lịch sử quiz nào
        </h3>

        <p className="text-gray-600 mb-8 max-w-md leading-relaxed text-lg">
          Bạn chưa hoàn thành quiz nào. Hãy bắt đầu hành trình học tập
          và khám phá kiến thức mới cùng chúng tôi!
        </p>

        {/* Feature highlights */}
        {/* <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mb-10 w-full max-w-2xl">
          <div className="group flex flex-col items-center p-6 bg-gradient-to-br from-blue-50 to-blue-100 rounded-xl border border-blue-200 transition-all duration-300 hover:shadow-lg hover:-translate-y-1">
            <div className="w-12 h-12 bg-gradient-to-br from-blue-500 to-blue-600 rounded-full flex items-center justify-center mb-3 group-hover:scale-110 transition-transform duration-300">
              <BookOpen className="w-6 h-6 text-white" />
            </div>
            <h4 className="font-semibold text-blue-800 mb-1">Học kiến thức</h4>
            <span className="text-sm text-blue-700 text-center">Khám phá các chủ đề thú vị</span>
          </div>

          <div className="group flex flex-col items-center p-6 bg-gradient-to-br from-green-50 to-green-100 rounded-xl border border-green-200 transition-all duration-300 hover:shadow-lg hover:-translate-y-1">
            <div className="w-12 h-12 bg-gradient-to-br from-green-500 to-green-600 rounded-full flex items-center justify-center mb-3 group-hover:scale-110 transition-transform duration-300">
              <TrendingUp className="w-6 h-6 text-white" />
            </div>
            <h4 className="font-semibold text-green-800 mb-1">Theo dõi tiến độ</h4>
            <span className="text-sm text-green-700 text-center">Xem kết quả và cải thiện</span>
          </div>

          <div className="group flex flex-col items-center p-6 bg-gradient-to-br from-yellow-50 to-yellow-100 rounded-xl border border-yellow-200 transition-all duration-300 hover:shadow-lg hover:-translate-y-1">
            <div className="w-12 h-12 bg-gradient-to-br from-yellow-500 to-yellow-600 rounded-full flex items-center justify-center mb-3 group-hover:scale-110 transition-transform duration-300">
              <Award className="w-6 h-6 text-white" />
            </div>
            <h4 className="font-semibold text-yellow-800 mb-1">Nhận thành tích</h4>
            <span className="text-sm text-yellow-700 text-center">Mở khóa huy hiệu và điểm số</span>
          </div>
        </div> */}

        {/* Action buttons */}
        <div className="flex flex-col sm:flex-row gap-4 mb-6">
          <button
            onClick={() => router.push(`../${lessonId}`)}
            className="group bg-gradient-to-r from-indigo-500 to-purple-600 hover:from-indigo-600 hover:to-purple-700 text-white font-semibold py-3 px-8 rounded-full transition-all duration-300 transform hover:scale-105 hover:shadow-lg flex items-center justify-center gap-2 min-w-[200px]"
          >
            Bắt đầu quiz ngay
            <ArrowRight className="w-4 h-4 group-hover:translate-x-1 transition-transform duration-300" />
          </button>

          <button
            // onClick={onBrowseQuizzes}
            className="group bg-white border-2 border-gray-300 hover:border-indigo-400 text-gray-700 hover:text-indigo-700 font-semibold py-3 px-8 rounded-full transition-all duration-300 flex items-center justify-center gap-2 min-w-[200px]"
          >
            <Search className="w-4 h-4" />
            Khám phá quiz
          </button>
        </div>

        {/* Stats or additional info */}
        <div className="flex items-center gap-6 text-sm text-gray-500">
          <div className="flex items-center gap-1">
            <div className="w-2 h-2 bg-green-400 rounded-full animate-pulse"></div>
            <span>100+ quiz có sẵn</span>
          </div>
          <div className="flex items-center gap-1">
            <div className="w-2 h-2 bg-blue-400 rounded-full animate-pulse delay-150"></div>
            <span>Nhiều chủ đề khác nhau</span>
          </div>
          <div className="flex items-center gap-1">
            <div className="w-2 h-2 bg-purple-400 rounded-full animate-pulse delay-300"></div>
            <span>Miễn phí tham gia</span>
          </div>
        </div>
      </div>
    );
  }
};

export default QuizHistoryInterface;