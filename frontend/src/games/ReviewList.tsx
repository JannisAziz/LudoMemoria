import {Review} from "./Review";
import ReviewCard from "./ReviewCard";

export default function ReviewList({reviews}: {reviews: Review[]}) {
    return (
        <ul>
            {reviews.map(review => (<li key={review.id}><ReviewCard review={review}/></li>))}
        </ul>
    )
}