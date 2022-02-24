import {Review} from "./Review";
import axios from "axios";
import {getAxiosConfig} from "../login/LoginService";

export function findReviewsByUserId(userId: string) {
   return axios
       .get(`/api/reviews/userId=${userId}`, getAxiosConfig())
       .then(response => response.data as Review[])
}

export function findReviewsByGameId(gameId: string) {
   return axios
       .get(`/api/reviews/gameId=${gameId}`, getAxiosConfig())
       .then(response => response.data as Review[])
}

export function addReview(review: Review) {
   return axios
       .put(`/api/reviews/`, review, getAxiosConfig())
       .then(response => response.data as Review)
}

export function updateReview(review: Review) {
   return axios
       .patch(`/api/reviews/`, review, getAxiosConfig())
       .then(response => response.data as Review)
}

export function deleteReview(review: Review) {
   return axios
       .delete(`/api/reviews/${review.id}`, getAxiosConfig())
       .then(response => response.data as Review)
}
