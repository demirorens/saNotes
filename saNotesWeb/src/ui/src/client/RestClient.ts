export class RestClient {

    static baseUrl = "http://localhost:8080/api"

    static async getNoteBooks() : Promise<any> {
        const url = `${RestClient.baseUrl}/notebooks`
        const response = await fetch(url)
        return await response.json()
    }

    static async getTags() : Promise<any> {
        const url = `${RestClient.baseUrl}/tags`
        const response = await fetch(url)
        return await response.json()
    }

    static async getNoteBook(id: number) : Promise<any> {
        const url = `${RestClient.baseUrl}/notebooks/${id}`
        const response = await fetch(url)
        return await response.json()
    }

    static async getTag(id: number) : Promise<any> {
        const url = `${RestClient.baseUrl}/tags/${id}`
        const response = await fetch(url)
        return await response.json()
    }

    static addNoteBook( noteBook: any) : Promise<any> {
        const url = `${RestClient.baseUrl}/notebooks/addnotebook`
        return fetch(
                    url, 
                    { 
                        method: 'POST', 
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify(noteBook)
                    }
        )
    }

    static addNote(noteBookId: number, note: any) : Promise<any> {
        const url = `${RestClient.baseUrl}/notebooks/addnote/${noteBookId}`
        return fetch(
                    url, 
                    { 
                        method: 'POST', 
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify(note)
                    }
        )
    }

    static addTag( tag: any) : Promise<any> {
        const url = `${RestClient.baseUrl}/tags/addtag`
        return fetch(
                    url, 
                    { 
                        method: 'POST', 
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify(tag)
                    }
        )
    }
}