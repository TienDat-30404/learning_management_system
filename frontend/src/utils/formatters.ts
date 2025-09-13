export const formatPrice = (amount: number): string => {
    return new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(amount);
};

export const formatTime = (second: number): string => {
    if (second < 60) {
        return `0:0:${second} giây`;
    }
    else if (second >= 60 && second < 3600) {
        if (second / 60 < 10) {
            return `0:0${Math.floor(second / 60)}:${second % 60}`;
        }
        else {
            return `0:${Math.floor(second / 60)}:${second % 60}`;
        }
    }
    else {
        return `${second / 3600}:${(second % 3600) / 60}:${second % 3600 % 60}`
    }

}