export async function request<T>(url: string, options?: RequestInit): Promise<T> {
    const res = await fetch(url, {...options, credentials: "include"});
    if(!res.ok){
        const errorText = await res.text().catch(() => "");
        throw new Error(`Request failed: ${res.status} - ${errorText}`);
    }
    const text = await res.text();
    return text ? JSON.parse(text) : (null as unknown as T);
}