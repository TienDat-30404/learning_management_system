import { useGetAllReview } from "@/hooks/useReviews"
import { Review } from "@/types/review";
import { Star } from "lucide-react";

interface ReviewCourseProps {
    idCourse: number; // Component này mong đợi prop idCourse là một số
}

const renderStars = (rating: number) => {
    return Array.from({ length: 5 }, (_, i) => (
      <Star
        key={i}
        className={`w-4 h-4 ${i < rating ? 'text-yellow-400 fill-current' : 'text-gray-300'}`}
      />
    ));
  };

export const ReviewCourse: React.FC<ReviewCourseProps> = ({ idCourse }) => {
    const { data: courseReviews, isLoading, error } = useGetAllReview({
        targetType: "COURSE", targetId: idCourse
    })

    console.log("jernje", courseReviews?.data)
    return (
        <div className="space-y-6">
            <div className="flex items-center justify-between">
                <h3 className="text-xl font-semibold">Đánh giá học viên </h3>
                <div className="flex items-center gap-2">
                    <div className="flex items-center gap-1">
                        {/* {renderStars(Math.floor(courseData.rating))} */}
                        
                    </div>
                    {/* <span className="font-semibold">{courseData.rating}</span> */}
                    {/* <span className="text-gray-500">({courseData.totalReviews} đánh giá)</span> */}
                </div>
            </div>

            <div className="space-y-4">
                {courseReviews?.data?.content?.map((review : Review) => (
                    <div key={review.id} className="border-b border-gray-200 pb-4 last:border-b-0">
                        <div className="flex items-start gap-3">
                            <img
                                src="https://tse4.mm.bing.net/th/id/OIP.lyqCwFzzjenJ_vptTF479AHaHa?pid=Api&P=0&h=180"
                                className="w-10 h-10 rounded-full object-cover"
                            />
                            <div className="flex-1">
                                <div className="flex items-center gap-2 mb-1">
                                    <span className="font-medium">{review?.user?.fullName}</span>
                                    <div className="flex items-center gap-1">
                                        {renderStars(review.rating)}
                                    </div>
                                    <span className="text-sm text-gray-500">{new Date(review.createdAt).toLocaleDateString('vi-VN')}</span>
                                </div>
                                <p className="text-gray-700">{review.comment}</p>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    )
}