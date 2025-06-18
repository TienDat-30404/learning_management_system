export interface LoginFormData {
    userName : string,
    password : string
}

export interface RegisterFormData  {
    userName : string,
    fullName : string,
    email : string,
    password : string,
    gender : string,
    birthDate : string,
    agreeTerms : boolean
}

export type ValidationErrors<T> = {
  [K in keyof T]?: string;
};

// Validation errors cho LoginFormData
export type ValidationErrorsLogin = ValidationErrors<LoginFormData>;

// Validation errors cho RegisterFormData
export type ValidationErrorsRegister = ValidationErrors<RegisterFormData>;

