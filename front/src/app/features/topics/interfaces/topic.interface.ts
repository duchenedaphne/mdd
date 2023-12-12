
export interface Topic {
    
    id?: number;
    name: string;
    description: string;
    users: number[];
    createdAt?: Date;
    updatedAt?: Date;
}
