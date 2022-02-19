import {Review} from "./Review";
import axios from "axios";

const getTestReviewRepo = (): Review[] => JSON.parse(localStorage.getItem("TESTDB") || "[]")
const setTestReviewRepo = (reviews: Review[]) =>  localStorage.setItem("TESTDB", JSON.stringify(reviews))
const clearTestReviewRepo = () => localStorage.removeItem("TESTDB")

export function addReview(review: Review) {
    const reviews = getTestReviewRepo()
    reviews.splice(-1, 0, review)
    setTestReviewRepo([...getTestReviewRepo(), review])
    return review
//    return axios
//        .put(`/api/reviews/`, review, getAxiosConfig())
//        .then(response => response.data)
//        .catch(console.error)
}

export function deleteReview(review: Review) {
    const reviews = getTestReviewRepo()
    reviews.splice(reviews.indexOf(review),1)
    setTestReviewRepo([...reviews])
//    return axios
//        .delete(`/api/reviews/${review.id}`, getAxiosConfig())
//        .then(response => response.data)
//        .catch(console.error)
}

export function updateReview(review: Review) {
    const reviews = getTestReviewRepo()
    reviews.splice(reviews.indexOf(review), 1, review)
    setTestReviewRepo([...reviews])
    return review
//    return axios
//        .patch(`/api/reviews/`, review, getAxiosConfig())
//        .then(response => response.data)
//        .catch(console.error)
}

export function findReviewsByGameId(gameId: string) {
    return getTestReviewRepo().filter(r => r.gameId === gameId)
}

export function findReviewsByUserId(userId: string) {
    return getTestReviewRepo().filter(r => r.userId === userId)

}
