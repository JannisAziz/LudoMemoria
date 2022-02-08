import {Review} from "../models/Review";

export default function ReviewCard({review}: {review: Review}) {
    return (
        <div>Review-{review.id}: {review.gameId}</div>
    )
}