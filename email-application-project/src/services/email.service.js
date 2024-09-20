import { customExios } from "./helper";

export async function sendEmail(emailData){
    const result = (await customExios.post('/email/send', emailData)).data
    return result;
}