
export interface Comment {
    
    id?: number;
    content: string;
    post_id: number;
    user_id: number;
    createdAt?: Date;
    updatedAt?: Date;
}
