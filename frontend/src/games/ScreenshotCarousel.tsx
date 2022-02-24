import {getImgUrl} from "./Game";
import React from "react";
import {Carousel} from "react-responsive-carousel";
import "react-responsive-carousel/lib/styles/carousel.min.css"; // requires a loader

export default function ScreenshotCarousel({screenshotIds, imgSize}: {screenshotIds: string[], imgSize?: string}) {
    return (
        <Carousel className={"ScreenshotCarousel"} showThumbs={false} autoPlay={true} infiniteLoop={true}>
            {screenshotIds.map(
                (id, idx) =>
                    (<img key={idx} src={getImgUrl(id, imgSize || "screenshot_big")} alt={"screenshot"}/>)
            )}
        </Carousel>
    )
}
