'use client'
import React, { useState } from 'react'
import MessagesStudent from './MessagesStudent'

export default function IconChat() {
    const [isOpenChat, setIsOpenChat] = useState(false)
    return (
        <>
            <div 
                onClick={() => setIsOpenChat(true)}
                className='w-20 h-20 fixed bottom-5 right-5 cursor-pointer z-40' // Chỉnh lại vị trí cho hợp lý hơn
            >
                <svg viewBox="0 0 60 60" xmlns="http://www.w3.org/2000/svg">
                    <rect x="8" y="12" width="36" height="24" rx="8" fill="#4F46E5" />
                    <path d="M 16 36 L 8 44 L 20 36 Z" fill="#4F46E5" />
                    <circle cx="18" cy="24" r="2.5" fill="white" />
                    <circle cx="26" cy="24" r="2.5" fill="white" />
                    <circle cx="34" cy="24" r="2.5" fill="white" />
                </svg>
            </div>

            <MessagesStudent 
                isOpen={isOpenChat}
            />
        </>
    )
}
