
export interface Post {
    id?: number;
    title: string;
    content: string;
    topic_id: number;
    user_id: number;
    createdAt?: Date;
    updatedAt?: Date;
}
