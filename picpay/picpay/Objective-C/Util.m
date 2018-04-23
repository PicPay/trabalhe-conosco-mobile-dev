//
//  Utils.m
//  picpay
//
//  Created by Lucas Romano Marquez Rizzi on 22/04/2018.
//  Copyright © 2018 Lucas Romano Marquez Rizzi. All rights reserved.
//

#import "Util.h"

@implementation Util

+ (NSString *)formatString:(NSString *)string withFormat:(NSString *)formatacao {
    NSString *filter = formatacao;
    NSUInteger onOriginal = 0, onFilter = 0, onOutput = 0;
    
    char outputString[([filter length])];
    BOOL done = NO;
    
    while(onFilter < [filter length] && !done) {
        char filterChar = [filter characterAtIndex:onFilter];
        char originalChar = onOriginal >= string.length ? '\0' : [string characterAtIndex:onOriginal];
        switch (filterChar) {
            case '#':
                if (originalChar=='\0') {
                    // We have no more input numbers for the filter.  We're done.
                    done = YES;
                    break;
                    }
                if (isdigit(originalChar)) {
                    outputString[onOutput] = originalChar;
                    onOriginal++;
                    onFilter++;
                    onOutput++;
                } else {
                    onOriginal++;
                }
                break;
            default:
                // Any other character will automatically be inserted for the user as they type (spaces, - etc..) or deleted as they delete if there are more numbers to come.
                outputString[onOutput] = filterChar;
                onOutput++;
                onFilter++;
                if(originalChar == filterChar) {
                    onOriginal++;
                }
                break;
        }
    }
    outputString[onOutput] = '\0'; // Cap the output string
    return [[NSString stringWithUTF8String:outputString] mutableCopy];
}

@end;
