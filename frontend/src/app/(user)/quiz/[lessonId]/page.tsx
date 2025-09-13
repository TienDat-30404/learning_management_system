'use client'

import React, { useEffect, useState } from 'react';
import { CheckCircle, XCircle, Clock, BookOpen, Trophy, ArrowRight, ArrowLeft, RotateCcw, History } from 'lucide-react';
import { useGetAllQuestionOfQuiz } from '@/hooks/useQuizs';
import { useParams } from 'next/navigation';
import { Question } from '@/types/question';
import { Answer } from '@/types/answer';
import { useCompletedQuizQuestion } from '@/hooks/useQuizAttempt';
import { useRouter } from 'next/navigation';


export default function JavaQuizInterface() {

  const router = useRouter()

  const [currentQuestion, setCurrentQuestion] = useState(0);
  const [selectedAnswers, setSelectedAnswers] = useState<{ [key: number]: number }>({});
  const [showResults, setShowResults] = useState(false);
  const [quizStarted, setQuizStarted] = useState(false);
  const [timeElapsed, setTimeElapsed] = useState(0);

  useEffect(() => {
    if (quizStarted && !showResults) {
      const timer = setInterval(() => {
        setTimeElapsed(prev => prev + 1);
      }, 1000);
      return () => clearInterval(timer);
    }
  }, [quizStarted, showResults]);

  const handleAnswerSelect = (questionId: number, answerId: number) => {
    setSelectedAnswers(prev => ({
      ...prev,
      [questionId]: answerId
    }));
  };

  const calculateScore = () => {
    let correct = 0;
    quizs?.data?.questions.forEach((question: Question) => {
      const selectedAnswerId = selectedAnswers[question.id];
      const selectedAnswer = question.answers.find(a => a.id === selectedAnswerId);
      if (selectedAnswer && selectedAnswer.correct) {
        correct++;
      }
    });
    return correct;
  };

  const formatTime = (seconds: number) => {
    const mins = Math.floor(seconds / 60);
    const secs = seconds % 60;
    return `${mins.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}`;
  };

  const resetQuiz = () => {
    setCurrentQuestion(0);
    setSelectedAnswers({});
    setShowResults(false);
    setQuizStarted(false);
    setTimeElapsed(0);
  };


  const { mutate: markedCompletedQuizQuestion } = useCompletedQuizQuestion()
  const nextQuestion = () => {
    if (currentQuestion < quizs?.data?.questions.length - 1) {
      setCurrentQuestion(prev => prev + 1);
    } else {
      markedCompletedQuizQuestion({
        quizId: quizs?.data?.id,
        score: (calculateScore() / quizs?.data?.questions?.length) * 10,
        duration : timeElapsed
      })
      setShowResults(true);
    }
  };

  const prevQuestion = () => {
    if (currentQuestion > 0) {
      setCurrentQuestion(prev => prev - 1);
    }
  };


  const { lessonId } = useParams()
  const { data: quizs } = useGetAllQuestionOfQuiz({
    lessonId: lessonId
  });

  if (!quizStarted) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-blue-50 via-indigo-50 to-purple-50 flex items-center justify-center p-4">
        <div className="bg-white rounded-2xl shadow-2xl p-8 max-w-2xl w-full border border-gray-100">
          <div className="text-center mb-8">
            <div className="inline-flex items-center justify-center w-16 h-16 bg-gradient-to-r from-blue-500 to-indigo-600 rounded-full mb-4">
              <BookOpen className="w-8 h-8 text-white" />
            </div>
            <h1 className="text-3xl font-bold text-gray-800 mb-2">{quizs?.data?.title}</h1>
            <p className="text-gray-600 text-lg">{quizs?.data?.description}</p>
          </div>

          <div className="bg-gray-50 rounded-xl p-6 mb-8">
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div className="flex items-center">
                <BookOpen className="w-5 h-5 text-blue-500 mr-2" />
                <span className="text-gray-700">Bài học: {quizs?.data?.lesson?.title}</span>
              </div>

              <div className="flex items-center">
                <Trophy className="w-5 h-5 text-yellow-500 mr-2" />
                <span className="text-gray-700">{quizs?.data?.questions?.length} câu hỏi</span>
              </div>

              <div className="flex items-center cursor-pointer"
                onClick={() => router.push(`./history/${quizs?.data?.id}?lessonId=${lessonId}`)}
              >
                <History className="w-5 h-5 text-black-500 mr-2" />
                <span className="text-gray-700">Lịch sử làm bài</span>
              </div>
            </div>
          </div>


          <button
            onClick={() => setQuizStarted(true)}
            className="w-full bg-gradient-to-r from-blue-500 to-indigo-600 text-white font-semibold py-4 px-8 rounded-xl hover:from-blue-600 hover:to-indigo-700 transform hover:scale-105 transition-all duration-200 shadow-lg hover:shadow-xl flex items-center justify-center"
          >
            <span className="mr-2">Bắt đầu Quiz</span>
            <ArrowRight className="w-5 h-5" />
          </button>
        </div>
      </div>
    );
  }

  if (showResults) {
    const score = calculateScore();
    const percentage = Math.round((score / quizs?.data?.questions.length) * 100);

    return (
      <div className="min-h-screen bg-gradient-to-br from-green-50 via-emerald-50 to-teal-50 flex items-center justify-center p-4">
        <div className="bg-white rounded-2xl shadow-2xl p-8 max-w-4xl w-full border border-gray-100">
          <div className="text-center mb-8">
            <div className="inline-flex items-center justify-center w-20 h-20 bg-gradient-to-r from-green-500 to-emerald-600 rounded-full mb-4">
              <Trophy className="w-10 h-10 text-white" />
            </div>
            <h2 className="text-3xl font-bold text-gray-800 mb-2">Kết quả Quiz</h2>
            <p className="text-gray-600">Bạn đã hoàn thành bài kiểm tra!</p>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
            <div className="bg-blue-50 rounded-xl p-6 text-center">
              <div className="text-3xl font-bold text-blue-600 mb-2">{score}/{quizs?.data?.questions.length}</div>
              <div className="text-gray-600">Câu đúng</div>
            </div>
            <div className="bg-purple-50 rounded-xl p-6 text-center">
              {/* <div className="text-3xl font-bold text-purple-600 mb-2">{percentage}%</div> */}
              <div className="text-3xl font-bold text-purple-600 mb-2">{ ( calculateScore() / quizs?.data?.questions?.length ) * 10 }</div>
              <div className="text-gray-600">Điểm số</div>
            </div>
            <div className="bg-orange-50 rounded-xl p-6 text-center">
              <div className="text-3xl font-bold text-orange-600 mb-2">{formatTime(timeElapsed)}</div>
              <div className="text-gray-600">Thời gian</div>
            </div>
          </div>

          <div className="space-y-6 mb-8">
            {quizs?.data?.questions?.map((question: Question, index: number) => {
              const selectedAnswerId = selectedAnswers[question.id];
              const selectedAnswer = question.answers.find(a => a.id === selectedAnswerId);
              const correctAnswer = question.answers.find(a => a.correct);
              const isCorrect = selectedAnswer && selectedAnswer.correct;

              return (
                <div key={question?.id} className="bg-gray-50 rounded-xl p-6">
                  <div className="flex items-start mb-4">
                    <div className="flex-shrink-0 mr-4">
                      {isCorrect ? (
                        <CheckCircle className="w-6 h-6 text-green-500" />
                      ) : (
                        <XCircle className="w-6 h-6 text-red-500" />
                      )}
                    </div>
                    <div className="flex-1">
                      <h3 className="font-semibold text-gray-800 mb-2">
                        Câu {index + 1}: {question?.question}
                      </h3>
                      <div className="space-y-2">
                        <div className={`p-3 rounded-lg ${isCorrect ? 'bg-green-100 border border-green-300' : 'bg-red-100 border border-red-300'}`}>
                          <span className="font-medium">Bạn chọn: </span>
                          <span>{selectedAnswer ? selectedAnswer.answer : 'Chưa trả lời'}</span>
                        </div>
                        {!isCorrect && (
                          <div className="p-3 rounded-lg bg-green-100 border border-green-300">
                            <span className="font-medium">Đáp án đúng: </span>
                            <span>{correctAnswer?.answer}</span>
                          </div>
                        )}
                      </div>
                    </div>
                  </div>
                </div>
              );
            })}
          </div>

          <button
            onClick={resetQuiz}
            className="w-full bg-gradient-to-r from-blue-500 to-indigo-600 text-white font-semibold py-4 px-8 rounded-xl hover:from-blue-600 hover:to-indigo-700 transform hover:scale-105 transition-all duration-200 shadow-lg hover:shadow-xl flex items-center justify-center"
          >
            <RotateCcw className="w-5 h-5 mr-2" />
            Làm lại Quiz
          </button>
        </div>
      </div>
    );
  }

  const question = quizs?.data?.questions[currentQuestion];
  const selectedAnswerId = selectedAnswers[question.id];
  const progress = ((currentQuestion + 1) / quizs?.data?.questions.length) * 100;

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-50 via-blue-50 to-indigo-50 flex items-center justify-center p-4">
      <div className="bg-white rounded-2xl shadow-2xl p-8 max-w-4xl w-full border border-gray-100">
        {/* Header */}
        <div className="flex items-center justify-between mb-8">
          <div className="flex items-center">
            <Clock className="w-5 h-5 text-gray-500 mr-2" />
            <span className="text-gray-600">{formatTime(timeElapsed)}</span>
          </div>
          <div className="text-center">
            <div className="text-sm text-gray-500 mb-1">Câu hỏi</div>
            <div className="text-lg font-semibold text-gray-800">
              {currentQuestion + 1} / {quizs?.data?.questions.length}
            </div>
          </div>
        </div>

        {/* Progress Bar */}
        <div className="mb-8">
          <div className="flex justify-between text-sm text-gray-600 mb-2">
            <span>Tiến độ</span>
            <span>{Math.round(progress)}%</span>
          </div>
          <div className="w-full bg-gray-200 rounded-full h-2">
            <div
              className="bg-gradient-to-r from-blue-500 to-indigo-600 h-2 rounded-full transition-all duration-300"
              style={{ width: `${progress}%` }}
            />
          </div>
        </div>

        {/* Question */}
        <div className="mb-8">
          <h2 className="text-2xl font-bold text-gray-800 mb-6">{question.question}</h2>

          <div className="space-y-4">
            {question.answers.map((answer: Answer) => (
              <button
                key={answer.id}
                onClick={() => handleAnswerSelect(question.id, answer.id)}
                className={`w-full text-left p-4 rounded-xl border-2 transition-all duration-200 ${selectedAnswerId === answer.id
                  ? 'border-blue-500 bg-blue-50 text-blue-700'
                  : 'border-gray-200 hover:border-gray-300 hover:bg-gray-50'
                  }`}
              >
                <div className="flex items-center">
                  <div className={`w-5 h-5 rounded-full border-2 mr-4 flex items-center justify-center ${selectedAnswerId === answer.id
                    ? 'border-blue-500 bg-blue-500'
                    : 'border-gray-400'
                    }`}>
                    {selectedAnswerId === answer.id && (
                      <div className="w-2 h-2 bg-white rounded-full" />
                    )}
                  </div>
                  <span className="text-lg">{answer.answer}</span>
                </div>
              </button>
            ))}
          </div>
        </div>

        {/* Navigation */}
        <div className="flex justify-between items-center">
          <button
            onClick={prevQuestion}
            disabled={currentQuestion === 0}
            className={`flex items-center px-6 py-3 rounded-xl font-semibold transition-all duration-200 ${currentQuestion === 0
              ? 'bg-gray-100 text-gray-400 cursor-not-allowed'
              : 'bg-gray-200 text-gray-700 hover:bg-gray-300'
              }`}
          >
            <ArrowLeft className="w-5 h-5 mr-2" />
            Câu trước
          </button>

          <button
            onClick={nextQuestion}
            disabled={!selectedAnswerId}
            className={`flex items-center px-6 py-3 rounded-xl font-semibold transition-all duration-200 ${!selectedAnswerId
              ? 'bg-gray-100 text-gray-400 cursor-not-allowed'
              : 'bg-gradient-to-r from-blue-500 to-indigo-600 text-white hover:from-blue-600 hover:to-indigo-700 shadow-lg hover:shadow-xl'
              }`}
          >
            {currentQuestion === quizs?.data?.questions.length - 1 ? 'Hoàn thành' : 'Câu tiếp'}
            <ArrowRight className="w-5 h-5 ml-2" />
          </button>
        </div>
      </div>
    </div>
  );
}