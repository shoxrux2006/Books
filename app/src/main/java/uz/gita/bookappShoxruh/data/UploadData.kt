package uz.gita.bookappShoxruh.data


sealed interface UploadData {
    class Complete(val bookNameInStorage: String) : UploadData
    object Failture : UploadData
    object NoConnection : UploadData
    object Cancel : UploadData
    object Pause : UploadData
    class Progress(val progress: Int) : UploadData
}
