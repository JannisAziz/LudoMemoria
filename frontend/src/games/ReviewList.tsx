import {Review} from "./Review";
import ReviewCard from "./ReviewCard";
import "../styles/Styles.scss";

export default function ReviewList({reviews}: {reviews: Review[] | undefined}) {
    return (
        <ul className={"ReviewList"}>
            {reviews && reviews.length > 0 ?
                reviews.map(review => (<li key={review.id}><ReviewCard review={review}/></li>)) :
                ("No reviews yet")
            }
        </ul>
    )
}