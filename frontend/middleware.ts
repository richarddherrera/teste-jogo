import { NextResponse } from "next/server";
import type { NextRequest } from "next/server";

export function middleware(request: NextRequest) {
  const token = request.cookies.get("authToken")?.value || 
                request.headers.get("authorization")?.replace("Bearer ", "");

  const isAuthPage = request.nextUrl.pathname.startsWith("/login") || 
                     request.nextUrl.pathname.startsWith("/register");

  // If user is authenticated and trying to access auth pages, redirect to home
  if (token && isAuthPage) {
    return NextResponse.redirect(new URL("/", request.url));
  }

  // We used to protect the "times", "torneios" and "matchmaking" pages, but
  // the UX is much smoother if guests can at least browse those sections
  // without having to authenticate.  The API still accepts requests without a
  // token, but actions like creating a time or joining the queue will fail.
  // Therefore we simply no longer redirect here.

  return NextResponse.next();
}

export const config = {
  matcher: [
    "/login",
    "/register",
    "/times/:path*",
    "/torneios/:path*",
    "/matchmaking/:path*",
  ],
};
