import React, { useState } from 'react';
import {
  MessageCircle,
  User,
  Clock,
  Search,
  Filter,
  ChevronDown,
  Mail,
  MailOpen,
  Star,
  Reply
} from 'lucide-react';

interface Message {
  id: string;
  studentId: string;
  studentName: string;
  studentAvatar?: string;
  subject: string;
  content: string;
  timestamp: Date;
  isRead: boolean;
  isStarred: boolean;
  priority: 'low' | 'medium' | 'high';
  course: string;
  hasAttachment?: boolean;
}

interface MessagesStudentProps {
  isOpen: boolean;
}

const StudentMessagesList: React.FC<MessagesStudentProps> = ({ isOpen }) => {
  const [messages, setMessages] = useState<Message[]>([
    {
      id: '1',
      studentId: 'sv001',
      studentName: 'Nguyễn Văn An',
      subject: 'Thắc mắc về bài tập tuần 3',
      content: 'Em có thắc mắc về phần bài tập React Hook, không hiểu cách sử dụng useEffect trong trường hợp này...',
      timestamp: new Date('2024-09-25T09:30:00'),
      isRead: false,
      isStarred: true,
      priority: 'high',
      course: 'React Cơ bản'
    },
    {
      id: '2',
      studentId: 'sv002',
      studentName: 'Trần Thị Bình',
      subject: 'Xin phép nghỉ buổi học ngày mai',
      content: 'Chào cô, em xin phép nghỉ buổi học ngày mai do có việc gia đình đột xuất. Em sẽ xem lại video bài giảng...',
      timestamp: new Date('2024-09-25T08:15:00'),
      isRead: true,
      isStarred: false,
      priority: 'medium',
      course: 'JavaScript Nâng cao'
    },
    {
      id: '3',
      studentId: 'sv003',
      studentName: 'Lê Minh Cường',
      subject: 'Hỏi về dự án cuối khóa',
      content: 'Thầy ơi, em muốn hỏi về yêu cầu dự án cuối khóa. Có được sử dụng thư viện ngoài không ạ?',
      timestamp: new Date('2024-09-24T16:45:00'),
      isRead: true,
      isStarred: false,
      priority: 'medium',
      course: 'Node.js & Express'
    },
    {
      id: '4',
      studentId: 'sv004',
      studentName: 'Phạm Thu Hương',
      subject: 'Báo lỗi trang web khóa học',
      content: 'Em gặp lỗi khi truy cập vào module 4, trang web báo lỗi 404. Mong thầy cô kiểm tra giúp em.',
      timestamp: new Date('2024-09-24T14:20:00'),
      isRead: false,
      isStarred: false,
      priority: 'high',
      course: 'Web Development'
    },
    {
      id: '5',
      studentId: 'sv005',
      studentName: 'Đỗ Quang Minh',
      subject: 'Cảm ơn thầy về buổi hướng dẫn',
      content: 'Em cảm ơn thầy về buổi hướng dẫn hôm qua. Giờ em đã hiểu rõ hơn về cách optimize performance...',
      timestamp: new Date('2024-09-24T11:00:00'),
      isRead: true,
      isStarred: true,
      priority: 'low',
      course: 'React Advanced'
    }
  ]);

  const [searchTerm, setSearchTerm] = useState('');
  const [filterRead, setFilterRead] = useState<'all' | 'read' | 'unread'>('all');
  const [selectedMessage, setSelectedMessage] = useState<string | null>(null);

  const filteredMessages = messages.filter(message => {
    const matchesSearch = message.studentName.toLowerCase().includes(searchTerm.toLowerCase()) ||
      message.subject.toLowerCase().includes(searchTerm.toLowerCase()) ||
      message.course.toLowerCase().includes(searchTerm.toLowerCase());

    const matchesFilter = filterRead === 'all' ||
      (filterRead === 'read' && message.isRead) ||
      (filterRead === 'unread' && !message.isRead);

    return matchesSearch && matchesFilter;
  });

  const toggleRead = (messageId: string) => {
    setMessages(messages.map(msg =>
      msg.id === messageId ? { ...msg, isRead: !msg.isRead } : msg
    ));
  };

  const toggleStar = (messageId: string) => {
    setMessages(messages.map(msg =>
      msg.id === messageId ? { ...msg, isStarred: !msg.isStarred } : msg
    ));
  };

  const getPriorityColor = (priority: string) => {
    switch (priority) {
      case 'high': return 'text-red-500';
      case 'medium': return 'text-yellow-500';
      case 'low': return 'text-green-500';
      default: return 'text-gray-500';
    }
  };

  const formatTime = (date: Date) => {
    const now = new Date();
    const diff = now.getTime() - date.getTime();
    const hours = Math.floor(diff / (1000 * 60 * 60));

    if (hours < 1) return 'Vừa xong';
    if (hours < 24) return `${hours}h trước`;
    return date.toLocaleDateString('vi-VN');
  };

  return (
    <div className={`fixed inset-0 ${isOpen ? 'flex' : 'hidden'} bg-transparent bg-opacity-40 flex items-center justify-end z-50 p-4`}>
      <div className="bg-white rounded-2xl shadow-xl w-full border-1 border-gray-400 max-w-2xl max-h-[90vh] overflow-y-auto">


        {/* Header */}
        <div className="bg-white rounded-lg shadow-sm p-6 mb-6">
          <div className="flex items-center justify-between mb-4">
            <div className="flex items-center gap-3">
              <MessageCircle className="w-8 h-8 text-blue-600" />
              <div>
                <h1 className="text-2xl font-bold text-gray-900">Tin nhắn học viên</h1>
                <p className="text-gray-600">Quản lý tin nhắn từ học viên trong hệ thống</p>
              </div>
            </div>
            <div className="text-sm text-gray-500">
              {messages.filter(m => !m.isRead).length} tin nhắn chưa đọc
            </div>
          </div>

          {/* Search and Filter */}
          <div className="flex gap-4">
            <div className="flex-1 relative">
              <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 w-5 h-5" />
              <input
                type="text"
                placeholder="Tìm kiếm theo tên, tiêu đề hoặc khóa học..."
                className="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
              />
            </div>
            <div className="relative">
              <select
                className="appearance-none bg-white border border-gray-300 rounded-lg px-4 py-2 pr-8 focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                value={filterRead}
                onChange={(e) => setFilterRead(e.target.value as 'all' | 'read' | 'unread')}
              >
                <option value="all">Tất cả</option>
                <option value="unread">Chưa đọc</option>
                <option value="read">Đã đọc</option>
              </select>
              <ChevronDown className="absolute right-2 top-1/2 transform -translate-y-1/2 text-gray-400 w-4 h-4 pointer-events-none" />
            </div>
          </div>
        </div>

        {/* Messages List */}
        <div className="bg-white rounded-b-lg shadow-sm overflow-hidden flex-1 overflow-y-auto">
          <div className="divide-y divide-gray-200">
            {filteredMessages.map((message) => (
              <div
                key={message.id}
                className={`p-4 hover:bg-gray-50 cursor-pointer transition-colors ${!message.isRead ? 'bg-blue-50 border-l-4 border-blue-500' : ''
                  } ${selectedMessage === message.id ? 'bg-blue-100' : ''}`}
                onClick={() => setSelectedMessage(selectedMessage === message.id ? null : message.id)}
              >
                <div className="flex items-start justify-between">
                  <div className="flex items-start gap-4 flex-1">
                    {/* Avatar */}
                    <div className="w-10 h-10 bg-gradient-to-br from-blue-500 to-purple-600 rounded-full flex items-center justify-center text-white font-medium">
                      {message.studentName.charAt(0)}
                    </div>

                    {/* Message Content */}
                    <div className="flex-1 min-w-0">
                      <div className="flex items-center gap-2 mb-1">
                        <h3 className={`font-medium ${!message.isRead ? 'text-gray-900' : 'text-gray-700'}`}>
                          {message.studentName}
                        </h3>
                        <span className={`inline-block w-2 h-2 rounded-full ${getPriorityColor(message.priority)}`} />
                        <span className="text-xs text-gray-500 bg-gray-100 px-2 py-1 rounded-full">
                          {message.course}
                        </span>
                      </div>

                      <p className={`font-medium mb-1 ${!message.isRead ? 'text-gray-900' : 'text-gray-600'}`}>
                        {message.subject}
                      </p>

                      <p className="text-sm text-gray-500 truncate">
                        {message.content}
                      </p>

                      <div className="flex items-center gap-4 mt-2 text-xs text-gray-400">
                        <span className="flex items-center gap-1">
                          <Clock className="w-3 h-3" />
                          {formatTime(message.timestamp)}
                        </span>
                        <span>ID: {message.studentId}</span>
                      </div>
                    </div>
                  </div>

                  {/* Actions */}
                  <div className="flex items-center gap-2 ml-4">
                    <button
                      onClick={(e) => {
                        e.stopPropagation();
                        toggleStar(message.id);
                      }}
                      className={`p-1 rounded hover:bg-gray-200 transition-colors ${message.isStarred ? 'text-yellow-500' : 'text-gray-400'
                        }`}
                    >
                      <Star className={`w-4 h-4 ${message.isStarred ? 'fill-current' : ''}`} />
                    </button>

                    <button
                      onClick={(e) => {
                        e.stopPropagation();
                        toggleRead(message.id);
                      }}
                      className="p-1 rounded hover:bg-gray-200 transition-colors text-gray-400"
                    >
                      {message.isRead ? <Mail className="w-4 h-4" /> : <MailOpen className="w-4 h-4" />}
                    </button>

                    <button className="p-1 rounded hover:bg-gray-200 transition-colors text-gray-400">
                      <Reply className="w-4 h-4" />
                    </button>
                  </div>
                </div>

                {/* Expanded Content */}
                {selectedMessage === message.id && (
                  <div className="mt-4 p-4 bg-gray-50 rounded-lg">
                    <div className="prose prose-sm max-w-none">
                      <p className="text-gray-700 whitespace-pre-wrap">{message.content}</p>
                    </div>

                    <div className="flex items-center gap-2 mt-4 pt-4 border-t border-gray-200">
                      <button className="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors flex items-center gap-2">
                        <Reply className="w-4 h-4" />
                        Trả lời
                      </button>
                      <button className="bg-gray-100 text-gray-700 px-4 py-2 rounded-lg hover:bg-gray-200 transition-colors">
                        Chuyển tiếp
                      </button>
                      <button className="bg-gray-100 text-gray-700 px-4 py-2 rounded-lg hover:bg-gray-200 transition-colors">
                        Lưu trữ
                      </button>
                    </div>
                  </div>
                )}
              </div>
            ))}
          </div>

          {filteredMessages.length === 0 && (
            <div className="p-12 text-center text-gray-500">
              <MessageCircle className="w-12 h-12 mx-auto mb-4 text-gray-300" />
              <p>Không tìm thấy tin nhắn nào</p>
            </div>
          )}
        </div>
        </div>
      </div>
      );
};

      export default StudentMessagesList;