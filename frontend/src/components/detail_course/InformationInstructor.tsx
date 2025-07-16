import { useGetAllReview } from "@/hooks/useReviews"
import { useGetDetailUser } from "@/hooks/useUsers";
import { Review } from "@/types/review";
import { Star, Users } from "lucide-react";

interface InformationCourseProps {
    idUser: number;
}



export const InformationInstructor: React.FC<InformationCourseProps> = ({ idUser }) => {
    const { data: detailUser, isLoading, error } = useGetDetailUser(idUser)

    console.log("detailUser", detailUser)
    return (
        <div className="space-y-6">
            <div className="flex items-start gap-4">
                <img
                    src="https://tse2.mm.bing.net/th/id/OIP.AbGafkazjc_S1pZPh0B9cQHaIm?pid=Api&P=0&h=180"
                    className="w-16 h-16 rounded-full object-cover"
                />
                <div>
                    <h3 className="text-xl font-semibold">{detailUser?.data?.fullName}</h3>
                    <p className="text-gray-600 mb-2">Test mô tả giảng viên</p>
                    <div className="flex items-center gap-4 text-sm text-gray-600">
                        <div className="flex items-center gap-1">
                            <Star className="w-4 h-4 text-yellow-400 fill-current" />
                            <span>test đánh giá</span>
                        </div>
                        <div className="flex items-center gap-1">
                            <Users className="w-4 h-4" />
                            <span>test học viên</span>
                        </div>
                    </div>
                </div>
            </div>
            <p className="text-gray-700 leading-relaxed">test - Đây là những gì bio của giảng viên</p>
        </div>
    )
}