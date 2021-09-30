

struct ListNode
{
    int value;
    struct ListNode *sig;
};

struct ListNode* reverse(struct ListNode* head){
    if ( head == 0 || head->sig == 0){
        return head;
    }
    else{
        struct ListNode *newHead;
        newHead = reverse(head->sig);
        struct ListNode *last = head->sig;
    }
}