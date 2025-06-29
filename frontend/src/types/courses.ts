

export interface Course {
    id : number,
    title : string,
    image : string,
    category : {
        name : string
    }
    price : number,
    user : {
        fullName : string
    }
}