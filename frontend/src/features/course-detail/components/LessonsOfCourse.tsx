import { useGetAllLessons } from "@/hooks/useLessons";
import { useGetAllReview } from "@/hooks/useReviews"
import { useGetDetailUser } from "@/hooks/useUsers";
import { Lesson } from "@/types/lesson";
import { Review } from "@/types/review";
import { CheckCircle, Play, Star } from "lucide-react";
import { Fragment } from "react";

interface LessonOfCourseProps {
  courseId: number;
}



export const LessonsOfCourse: React.FC<LessonOfCourseProps> = ({ courseId }) => {
  const { data: lessons, isLoading, error } = useGetAllLessons({
    courseId: courseId
  })

  console.log("lessons", lessons)
  return (
    <Fragment>
      <div className="flex items-center justify-between">
        <h3 className="text-xl font-semibold">Nội dung khóa học</h3>
        <div className="text-sm text-gray-600">
          2 chương • 500 bài học • 2 giờ
        </div>
      </div>
      <div className="border-t border-gray-200">
        {lessons?.data?.content.map((lesson: Lesson) => (
          <div key={lesson.id} className="px-4 py-3 flex items-center justify-between border-b border-gray-100 last:border-b-0">
            <div className="flex items-center gap-3">
              <CheckCircle className="w-4 h-4 text-green-500" />
              {/* {lesson.locked && <Lock className="w-4 h-4 text-gray-400" />} */}
              <div >
                <p className="font-medium">{lesson.title}</p>
                <p className="text-sm text-gray-500">test giờ</p>
              </div>
            </div>
            <Play className="w-4 h-4 text-blue-600 cursor-pointer hover:text-blue-800" />

          </div>
        ))}
      </div>
    </Fragment>
  )
}