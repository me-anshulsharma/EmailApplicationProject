import React, { useRef, useState } from 'react';
import toast from 'react-hot-toast';
import { sendEmail } from '../services/email.service';
import { Editor } from '@tinymce/tinymce-react';



function EmailSender() {
    const [emailData, setEmailData] = useState({
        to: "",
        subject: "",
        message: "",
    });

    const [sending, setSending] = useState(false);
    const editorRef = useRef(null);


    function handleFieldChange(event, name) {
        setEmailData({ ...emailData, [name]: event.target.value });
    }

    // Submit handler needs to be async to handle the API call
    async function handleSubmit(event) {
        event.preventDefault();

        if (emailData.to === '' || emailData.subject === '' || emailData.message === '') {
            toast.error("Please fill in all fields.");
            return;
        }

        console.log(emailData);

        // Send email using API
        try {
            setSending(true);
            await sendEmail(emailData);
            toast.success("Email sent successfully.");
            setEmailData({
                to: "",
                subject: "",
                message: "",
            });
        } catch (error) {
            console.log(error);
            toast.error("Failed to send email.");
        } finally {
            setSending(false);
        }
    }

    function handleClear() {
        // Reset the emailData state to its initial values
        setEmailData({
            to: "",
            subject: "",
            message: "",
        });

        // Clear TinyMCE editor content
        if (editorRef.current) {
            editorRef.current.setContent(''); // Reset the editor's content to empty
        }
    }

    return (
        <div className='w-full min-h-screen flex justify-center items-center'>
            <div className='email_card w-full px-4 py-3 mx-4 md:mx-0 md:w-[90%] lg:w-[80%] max-w-3xl h-auto min-h-[300px] rounded-3xl border border-gray-200 shadow-2xl dark:border-zinc-800 dark:bg-zinc-800'>

                {/*  <div className='email_card w-full px-2 py-3 mx-2 md:mx-0 md:w-[80%] lg:w-[70%] max-w-xl h-auto min-h-[250px] rounded-2xl border border-gray-200 shadow-lg dark:border-zinc-700 dark:bg-zinc-700'>
 */}

                <h1 className="text-zinc-800 dark:text-zinc-100 text-4xl py-2 font-semibold mb-1 flex justify-center ">Email Application</h1>
                <p className="text-gray-50 dark:text-zinc-100 text-xs mb-6 flex justify-center ">
                    Efficiently compose and send emails with a user-friendly interface designed for modern communication.
                </p>

                <form action="" onSubmit={handleSubmit}>
                    {/* To whom you want to send email */}
                    <div className="input_field mt-3">
                        {/* <label htmlFor="to" className="block mb-1 text-xs font-semibold text-zinc-800 dark:text-zinc-100">
                            Recipient Email
                        </label> */}
                        <input
                            value={emailData.to}
                            onChange={(event) => handleFieldChange(event, "to")}
                            type="email"
                            id="to"
                            className="block w-full p-2 text-sm text-gray-800 border border-gray-300 rounded-2xl bg-gray-50 dark:bg-zinc-300 dark:border-zinc-600 dark:placeholder-zinc-500 dark:text-zinc-800"
                            placeholder="Enter recipient's address....."
                        />
                    </div>

                    <div className="input_field mt-3">
                        {/* <label htmlFor="subject" className="block mb-2 text-xs font-medium text-gray-600 dark:text-zinc-100">
                            Email Subject
                        </label> */}
                        <input
                            value={emailData.subject}
                            onChange={(event) => handleFieldChange(event, "subject")}
                            type="text"
                            id="subject"
                            className="block w-full p-2 text-sm text-gray-800 border border-gray-300 rounded-2xl bg-gray-50 dark:bg-zinc-300 dark:border-zinc-700  dark:placeholder-zinc-500 dark:text-zinc-800"
                            placeholder="Enter email subject....."
                        />
                    </div>


                    {/* Message Area */}
                    <div className="form_field mt-3">
                        {/* <label htmlFor="message" className="block mb-2 text-sm font-medium text-zinc-700 dark:text-zinc-100">
                            Message
                        </label> */}

                        <div className="border border-gray-300 dark:border-zinc-700 rounded-2xl bg-gray-50 dark:bg-zinc-800 p-2">
                            <Editor
                                onEditorChange={(content) => {
                                    setEmailData({ ...emailData, message: editorRef.current.getContent() });
                                }}
                                apiKey="7t154doy6lgwfngcqfc67vnjglhmaje1u7o0p4i05nsaf4hu"
                                onInit={(_evt, editor) => editorRef.current = editor}
                                // initialValue="<p>This is the initial content of the editor.</p>"
                                init={{
                                    placeholder: "Type your message here...",
                                    branding: false,
                                    plugins: [
                                        'anchor', 'autolink', 'charmap', 'codesample', 'emoticons', 'image', 'link', 'lists', 'media', 'searchreplace', 'table', 'visualblocks', 'wordcount',
                                        'checklist', 'mediaembed', 'casechange', 'export', 'formatpainter', 'pageembed', 'a11ychecker', 'tinymcespellchecker', 'permanentpen', 'powerpaste',
                                        'advtable', 'advcode', 'editimage', 'advtemplate', 'ai', 'mentions', 'tinycomments', 'tableofcontents', 'footnotes', 'mergetags', 'autocorrect',
                                        'typography', 'inlinecss', 'markdown',
                                    ],
                                    toolbar: 'undo redo | blocks fontfamily fontsize | bold italic underline strikethrough | link image media table mergetags | addcomment showcomments | spellcheckdialog a11ycheck typography | align lineheight | checklist numlist bullist indent outdent | emoticons charmap | removeformat',
                                    tinycomments_mode: 'embedded',
                                    tinycomments_author: 'Author name',
                                    mergetags_list: [
                                        { value: 'First.Name', title: 'First Name' },
                                        { value: 'Email', title: 'Email' },
                                    ],
                                    ai_request: (request, respondWith) => respondWith.string(() => Promise.reject('See docs to implement AI Assistant')),
                                }}
                            />
                        </div>
                    </div>


                    {/* Loader */}
                    {sending && (
                        <div className='loader flex justify-center mt-4 flex-col gap-2 items-center'>
                            <div role="status">
                                <svg aria-hidden="true" className="w-8 h-8 text-gray-200 animate-spin dark:text-gray-600 fill-blue-600" viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                                    <path d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z" fill="currentColor" />
                                    <path d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z" fill="currentFill" />
                                </svg>
                                <span className="sr-only">Loading...</span>
                            </div>
                            <h1 className="text-gray-700 dark:text-gray-300">Sending...</h1>
                        </div>
                    )}

                    {/* Send and Clear Buttons */}
                    <div className="button_container mt-3 flex justify-center gap-2">
                        <button
                            disabled={sending}
                            type='submit'
                            className='bg-blue-600 shadow-xl hover:bg-blue-700 text-white border text-sm border-blue-700 rounded-3xl px-5 py-2 dark:bg-blue-500 dark:border-blue-600 dark:hover:bg-blue-600'>
                            Send Email
                        </button>

                        <button
                            type="button" // To prevent form submission on clear
                            onClick={handleClear}
                            className='bg-red-600 shadow-xl hover:bg-red-700 text-white text-sm border border-red-700 rounded-3xl px-5 py-2 dark:bg-red-500 dark:border-red-600 dark:hover:bg-red-600'>
                            Clear
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default EmailSender;

