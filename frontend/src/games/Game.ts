export type Game = {
    id: string
    name: string
    description: string

    coverId: string
    screenshotIds: string[]
}

export function getImgUrl(imageId: string, size?: string) {
    return `https://images.igdb.com/igdb/image/upload/t_${size || "thumb"}/${imageId}.jpg`
}